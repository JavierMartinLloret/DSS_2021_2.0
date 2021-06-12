package com.bqh_2021.Abstract_Factory_DAO.Interfaces;

import java.util.Map;

import javax.crypto.SecretKey;

public interface IUserSecurityDAO {
    public Map<String, SecretKey> getUserSecurityKeys();
    public void postUserSecurityKey(Map<String, SecretKey> user_key);
}
