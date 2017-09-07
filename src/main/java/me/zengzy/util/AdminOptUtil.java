package me.zengzy.util;

import me.zengzy.repo.AdminOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminOptUtil {

    static AdminOptionRepository repository;

    @Autowired
    public AdminOptUtil(AdminOptionRepository repository){
        AdminOptUtil.repository = repository;
    }

    //获取报价页面条数
    public static int getOfferPageSize(){
        return Integer.parseInt(repository.queryByPrimaryKey(1).getOption_content());
    }

    //获取默认页面条数
    public static int getDftPageSize(){
        return Integer.parseInt(repository.queryByPrimaryKey(3).getOption_content());
    }
}
