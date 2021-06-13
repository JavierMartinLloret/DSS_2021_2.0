package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.math.BigDecimal;
import java.util.Map;

public interface IDayBoxDAO {
    public Map<String, BigDecimal> getDayBox();
    public void setDayBox(Map<String, BigDecimal> map);
}
