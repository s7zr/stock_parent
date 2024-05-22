package org.mmj.stock;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.mmj.stock.mapper.SysUserMapper;
import org.mmj.stock.pojo.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class TestPageHelper {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 测试分页
     */
    @Test
    public void test(){
        Integer page = 2;
        Integer pagesize=5;//每页大小
        PageHelper.startPage(page, pagesize);
        List<SysUser> all = sysUserMapper.findAll();//这里的all会变成Page类型
        //将查询的page对象封装到PageInfo下就可以获取分页的各种数据
        PageInfo<SysUser> pageInfo = new PageInfo<>(all);
        //获取分页的详情数据
        int pageNum = pageInfo.getPageNum();//获取当前页
        int pages = pageInfo.getPages();//获取总页数
        int pageSize1 = pageInfo.getPageSize();//每页大小
        int size = pageInfo.getSize();//当前页的记录数
        long total = pageInfo.getTotal();//总记录数
        List<SysUser> list = pageInfo.getList();//获取当前页的内容

        System.out.println(all);
    }
}
