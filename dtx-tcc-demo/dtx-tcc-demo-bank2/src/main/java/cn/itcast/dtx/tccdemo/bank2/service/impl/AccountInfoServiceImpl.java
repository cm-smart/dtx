package cn.itcast.dtx.tccdemo.bank2.service.impl;

import cn.itcast.dtx.tccdemo.bank2.dao.AccountInfoDao;
import cn.itcast.dtx.tccdemo.bank2.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountInfoDao accountInfoDao;

    @Transactional
    @Hmily(confirmMethod = "commit",cancelMethod = "rollback")
    @Override
    public void updateAccountBalance(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 try begin 开始执行...xid:{}",transId);
    }

    /**
     * confirm方法
     * confirm幂等校验
     * 正式增加金额
     * @param accountNo
     * @param amount
     */
    public void commit(String accountNo, Double amount){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 confirm begin 开始执行...xid:{}",transId);
        if(accountInfoDao.isExistConfirm(transId)>0){
            log.info("bank2 confirm 已经执行，无需重复执行...xid:{}",transId);
            return ;
        }
        //增加金额
        accountInfoDao.addAccountBalance(accountNo,amount);
        //增加一条confirm日志，用于幂等
        accountInfoDao.addConfirm(transId);
        log.info("bank2 confirm end 结束执行...xid:{}",transId);
    }

    /**
     *
     * @param accountNo
     * @param amount
     */
    public void rollback(String accountNo, Double amount){
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 cancel begin 开始执行...xid:{}",transId);
    }
}
