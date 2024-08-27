package com.thanos.web3j.model.event;

/**
 * CandidateEventConstant.java description：
 *
 * @Author laiyiyu create on 2021-03-08 15:00:43
 */
public class CandidateEventConstant {

    //process type
    // 加入
    public static final int REGISTER_PROCESS = 0;
    // 退出
    public static final int CANCEL_PROCESS = 1;

    //vote type
    // 同意
    public static final int AGREE_VOTE = 0;
    // 拒绝
    public static final int DISAGREE_VOTE = 1;
    // 撤销
    public static final int REVOKE_VOTE = 2;

    //vote state code
    //全局事件上链失败
    public static int VOTE_FAILED = 0;
    //投票成功，提案进行中
    public static int VOTE_SUCCESS = 1;
    //全局事件通过
    public static int AGREE_FINISH = 2;
    //投票成功，提案不通过
    public static int DISAGREE_FINISH = 3;
    //提案撤销成功
    public static int REVOKE_FINISH = 4;

}
