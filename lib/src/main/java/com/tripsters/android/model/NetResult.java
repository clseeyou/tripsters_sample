package com.tripsters.android.model;

/**
 * 趣皮士网络接口返回的父类
 * <p>
 * 返回值中的errorCode和对应的errmsg，解释如下：
 * <table border="1">
 *     <tr>
 *         <th>errorcode</th>
 *         <th>errmsg</th>
 *         <th>说明</th>
 *     </tr>
 *     <tr>
 *         <th>0</th>
 *         <th>-</th>
 *         <th>正常</th>
 *     </tr>
 *     <tr>
 *         <th>10001</th>
 *         <th>appid is emptey</th>
 *         <th>appid为空</th>
 *     </tr>
 *     <tr>
 *         <th>10002</th>
 *         <th>invalid appid</th>
 *         <th>错误的appid</th>
 *     </tr>
 *     <tr>
 *         <th>10003</th>
 *         <th>url is null</th>
 *         <th>接收回复的url为空</th>
 *     </tr>
 *     <tr>
 *         <th>10004</th>
 *         <th>url is null</th>
 *         <th>接收追问的url为空</th>
 *     </tr>
 *     <tr>
 *         <th>20001</th>
 *         <th>data is empty</th>
 *         <th>数据为空</th>
 *     </tr>
 *     <tr>
 *         <th>30001</th>
 *         <th>User information is not complete</th>
 *         <th>用户信息不完整，用户user_id，avatar，nickname，gender可能为空</th>
 *     </tr>
 *     <tr>
 *         <th>30002</th>
 *         <th>Gender information is not complete</th>
 *         <th>性别信息不完整，性别不是m，f，n中的一个值</th>
 *     </tr>
 *     <tr>
 *         <th>30003</th>
 *         <th>This user has been added to the blacklist</th>
 *         <th>用户被拉黑</th>
 *     </tr>
 *     <tr>
 *         <th>30004</th>
 *         <th>register error</th>
 *         <th>用户注册失败</th>
 *     </tr>
 *     <tr>
 *         <th>30005</th>
 *         <th>user does not exist</th>
 *         <th>用户不存在</th>
 *     </tr>
 *     <tr>
 *         <th>30006</th>
 *         <th>update error</th>
 *         <th>更新用户信息失败</th>
 *     </tr>
 *     <tr>
 *         <th>40001</th>
 *         <th>Question information is not complete</th>
 *         <th>提问信息不完整</th>
 *     </tr>
 *     <tr>
 *         <th>40002</th>
 *         <th>Data exceed the maximum value</th>
 *         <th>提问问题数超过最大数</th>
 *     </tr>
 *     <tr>
 *         <th>40003</th>
 *         <th>Reanswer information is not complete</th>
 *         <th>追问信息不完整</th>
 *     </tr>
 *     <tr>
 *         <th>40004</th>
 *         <th>Reply to the problem does not exist</th>
 *         <th>回复的问题不存在</th>
 *     </tr>
 *     <tr>
 *         <th>40005</th>
 *         <th>Reply to the User does not exist</th>
 *         <th>回复的人不存在</th>
 *     </tr>
 *     <tr>
 *         <th>40006</th>
 *         <th>city is not numeric</th>
 *         <th>城市的数据格式不对</th>
 *     </tr>
 *     <tr>
 *         <th>40007</th>
 *         <th>The number of abnormal City array</th>
 *         <th>提交的城市数量不对，城市至少一个，不超过2个</th>
 *     </tr>
 * </table>
 * </p>
 */
public class NetResult {

    protected static final int CODE_SUCCESS = 0;

    private int errorcode;
    private String errmsg;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "errorcode:" + errorcode + " errmsg:" + errmsg;
    }

    public boolean isSuccessful() {
        return CODE_SUCCESS == errorcode;
    }
}
