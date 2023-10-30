package com.bioproj.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.bioproj.utils.BaseResponse;
import com.bioproj.utils.PageBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/warehouse")
public class WareHouseController {

    @Value("${giteaUrl}")
    String giteaUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @GetMapping("list")
    public BaseResponse list(){
        String url = giteaUrl + "/api/v1/orgs/mbiolance-bioinfo/repos";
        JSONArray jsonArray = restTemplate.getForObject(url, JSONArray.class);
        List<Map<String,String>> mapList = new ArrayList<>();
        for (Object o : jsonArray) {
            Map<String,String> map = new HashMap<>();
            JSONObject o1 = (JSONObject) o;
            String id = o1.get("id").toString();
            String name = o1.get("name").toString();
            String description = o1.get("description").toString();
            String clone_url =   o1.get("clone_url").toString();
            map.put("id",id);
            map.put("name",name);
            map.put("description",description);
            map.put("clone_url",clone_url);
            mapList.add(map);
        }
        return BaseResponse.ok(mapList);
    }
    @GetMapping("page")
    public PageBean<Map<String,String>> page(@PageableDefault(sort = {"id"},direction = DESC) Pageable pageable){
        PageBean<Map<String,String>> pageBean = new PageBean<>();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        pageBean.setPageNo(pageNumber);
        pageBean.setPageSize(pageSize);
        Integer  skip= 0;
        if (pageNumber <= 1) {
            skip = 0;
        }else {
            skip = pageSize * pageNumber - pageSize;
        }
        List<Map<String, String>> data = (List<Map<String, String>>) this.list().getData();
        pageBean.setTotalRecords(data.size());
        List<Map<String, String>> mapList = data.stream().skip(skip).limit(pageSize).collect(Collectors.toList());
        pageBean.setList(mapList);
        return pageBean;
    }
}
