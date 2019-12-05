package cn.center.mapper;

import cn.center.pojo.TbShopEmployeeCustomerCashLog;
import cn.center.pojo.TbShopEmployeeCustomerCashLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbShopEmployeeCustomerCashLogMapper {
    long countByExample(TbShopEmployeeCustomerCashLogExample example);

    int deleteByExample(TbShopEmployeeCustomerCashLogExample example);

    int insert(TbShopEmployeeCustomerCashLog record);

    int insertSelective(TbShopEmployeeCustomerCashLog record);

    List<TbShopEmployeeCustomerCashLog> selectByExample(TbShopEmployeeCustomerCashLogExample example);

    int updateByExampleSelective(@Param("record") TbShopEmployeeCustomerCashLog record, @Param("example") TbShopEmployeeCustomerCashLogExample example);

    int updateByExample(@Param("record") TbShopEmployeeCustomerCashLog record, @Param("example") TbShopEmployeeCustomerCashLogExample example);
}