package com.lxh.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxh.entity.PageResult;
import com.lxh.entity.RedisConstant;
import com.lxh.entity.Setmeal;
import com.lxh.mapper.SetmealMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String outPutPath;//从属性文件中读取要生成的html对应的目录




    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealMapper.findPage(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void insertSetmeal(Setmeal setmeal, String[] checkgroupIds) {
        setmealMapper.insertSetmeal(setmeal);
        if (checkgroupIds!=null && checkgroupIds.length>0) {
            insertSetChe(setmeal.getId(), checkgroupIds);
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        generateMobileStaticHtml();
    }

    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //在生成静态页面之前需要查询数据
        List<Setmeal> list = setmealMapper.findAll();

        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);

        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap();
        //为模板提供数据，用于生成静态页面
        map.put("setmealList",list);
        generteHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    //生成套餐详情静态页面（可能有多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealMapper.findById(setmeal.getId()));
            generteHtml("mobile_setmeal_detail.ftl","setmeal_detail_" + setmeal.getId() + ".html",map);
        }
    }

    //通用的方法，用于生成静态页面
    public void generteHtml(String templateName,String htmlPageName,Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();//获得配置对象
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构造输出流
            out = new FileWriter(new File(outPutPath + "/" + htmlPageName));
            //输出文件
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Setmeal> findAll() {

        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(int id) {

        return setmealMapper.findById(id);
    }


    public void insertSetChe(int SetId,String[] checkgroupIds){
        setmealMapper.insertSetChe(SetId,checkgroupIds);
    }
}
