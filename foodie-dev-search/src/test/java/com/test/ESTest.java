package com.test;

import com.google.common.collect.Maps;
import com.zhangzz.SearchApplication;
import com.zhangzz.es.pojo.Stu;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author zhangzz
 * @date 2020/5/23 18:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用ElasticSearchTemplate对索引进行管理（创建索引、更新映射、删除索引）
     */

    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1002L);
        stu.setName("spider man");
        stu.setAge(22);
        stu.setMoney(18.8f);
        stu.setSign("I am spider man");
        stu.setDescription("I wish i am spider man");
        IndexQuery query = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(query);
    }

    @Test
    public void deleteIndexStu() {
        esTemplate.deleteIndex(Stu.class);
    }

    @Test
    public void updateStuDoc() {
        IndexRequest indexRequest = new IndexRequest();
        Map<String, Object> source = Maps.newHashMap();
        source.put("sign", "I am not super man");
        source.put("money", 88.6f);
        source.put("age", 33);
        indexRequest.source(source);
        UpdateQuery query = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1002")
                .withIndexRequest(indexRequest)
                .build();
        esTemplate.update(query);
    }

    @Test
    public void queryIndexStu() {
        GetQuery query = new GetQuery();
        query.setId("1002");
        Stu stu = esTemplate.queryForObject(query, Stu.class);
        System.out.println(stu);
    }

    @Test
    public void deleteStuDoc() {
        esTemplate.delete(Stu.class, "1002");
    }

    @Test
    public void searchStuDoc() {
        Pageable pageable = PageRequest.of(0, 10);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "spider"))
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> page = esTemplate.queryForPage(query, Stu.class);
        System.out.println("检索后的总分页数目为：" + page.getTotalPages());
        for (Stu stu : page.getContent()) {
            System.out.println(stu);
        }
    }

    @Test
    public void highlightStuDoc() {
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        Pageable pageable = PageRequest.of(0, 10);
        FieldSortBuilder sortBuilder = new FieldSortBuilder("money").order(SortOrder.DESC);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "spider"))
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag)
                        .postTags(postTag))
                .withSort(sortBuilder)
                .withPageable(pageable)
                .build();
        SearchResultMapper mapper = new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Stu> stuListHighlight = Lists.newArrayList();
                for (SearchHit hit : searchResponse.getHits()) {
                    HighlightField highlightField = hit.getHighlightFields().get("description");
                    String description = highlightField.getFragments()[0].toString();
                    Map<String, Object> map = hit.getSourceAsMap();
                    Stu stu = new Stu();
                    stu.setDescription(description);
                    stu.setStuId(Long.parseLong(String.valueOf(map.get("stuId"))));
                    stu.setName(String.valueOf(map.get("name")));
                    stu.setAge(Integer.parseInt(String.valueOf(map.get("stuId"))));
                    stu.setSign(String.valueOf(map.get("sign")));
                    stu.setMoney(Float.parseFloat(String.valueOf(map.get("stuId"))));
                    stuListHighlight.add(stu);
                }
                return new AggregatedPageImpl<>((List<T>) stuListHighlight);
            }
        };
        AggregatedPage<Stu> page = esTemplate.queryForPage(query, Stu.class, mapper);
        System.out.println("检索后的总分页数目为：" + page.getTotalPages());
        for (Stu stu : page.getContent()) {
            System.out.println(stu);
        }
    }

}
