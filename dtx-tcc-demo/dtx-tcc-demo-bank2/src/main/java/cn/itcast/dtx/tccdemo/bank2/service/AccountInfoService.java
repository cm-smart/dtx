package cn.itcast.dtx.tccdemo.bank2.service;

public interface AccountInfoService {
    //账户扣款
    public  void updateAccountBalance(String accountNo, Double amount);
}
