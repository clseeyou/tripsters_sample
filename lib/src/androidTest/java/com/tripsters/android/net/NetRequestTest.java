package com.tripsters.android.net;

import android.test.AndroidTestCase;

import com.tripsters.android.model.AnswerList;
import com.tripsters.android.model.CityList;
import com.tripsters.android.model.CountryList;
import com.tripsters.android.model.Gender;
import com.tripsters.android.model.NetResult;
import com.tripsters.android.model.QuestionList;
import com.tripsters.android.model.QuestionResult;
import com.tripsters.android.model.TagList;
import com.tripsters.android.model.UserInfoResult;
import com.tripsters.android.util.Constants;

/**
 * Created by chenli on 16/2/22.
 */
public class NetRequestTest extends AndroidTestCase {

    public void testGetSupportCountry() throws Exception {
        CountryList countryList = NetRequest.getSupportCountry(getContext());
        assertNotNull(countryList);
        assertTrue(countryList.isSuccessful());
        assertFalse(countryList.getList().isEmpty());
    }

    public void testGetSupportCity() throws Exception {
        CityList cityList = NetRequest.getSupportCity(getContext(), "th");
        assertNotNull(cityList);
        assertTrue(cityList.isSuccessful());
        assertFalse(cityList.getList().isEmpty());
    }

    public void testGetSupportCate() throws Exception {
        TagList tagList = NetRequest.getSupportCate(getContext());
        assertNotNull(tagList);
        assertTrue(tagList.isSuccessful());
        assertFalse(tagList.getList().isEmpty());
    }

    public void testSendQuestionById() throws Exception {
        NetResult netResult = NetRequest.sendQuestionById(getContext(), getTestUid(), "question", null, "泰国", "33,34", null, null, null, null);
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testGetAnswer() throws Exception {
        AnswerList tagList = NetRequest.getAnswer(getContext(), "14849", 1, Constants.PAGE_COUNT);
        assertNotNull(tagList);
        assertTrue(tagList.isSuccessful());
        assertFalse(tagList.getList().isEmpty());
    }

    public void testGetAllQuestion() throws Exception {
        QuestionList questionList = NetRequest.getAllQuestion(getContext(), "", 1, Constants.PAGE_COUNT);
        assertNotNull(questionList);
        assertTrue(questionList.isSuccessful());
        assertFalse(questionList.getList().isEmpty());
    }

    public void testGetAppQuestion() throws Exception {
        QuestionList questionList = NetRequest.getAppQuestion(getContext(), "", 1, Constants.PAGE_COUNT);
        assertNotNull(questionList);
        assertTrue(questionList.isSuccessful());
        assertFalse(questionList.getList().isEmpty());
    }

    public void testGetQuestionDetail() throws Exception {
        QuestionResult questionResult = NetRequest.getQuestionDetail(getContext(), "14849");
        assertNotNull(questionResult);
        assertTrue(questionResult.isSuccessful());
        assertNotNull(questionResult.getQuestion());
    }

    public void testSendAnswerById() throws Exception {
        NetResult netResult = NetRequest.sendAnswerById(getContext(), getTestUid(), "answer", null, null, null, "14849", null);
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testSendReAnswerById() throws Exception {
        NetResult netResult = NetRequest.sendReAnswerById(getContext(), getTestUid(), "reanswer", null, null, null, "14849", null, getTestUid());
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testLogin() throws Exception {
        UserInfoResult UserInfoResult = NetRequest.login(getContext(), "1", "android", "http://a.hiphotos.baidu.com/baike/w%3D268/sign=b63699f3a8773912c4268267c0188675/10dfa9ec8a13632722e51d7d908fa0ec08fac770.jpg", Gender.MALE.getValue(), null);
        assertNotNull(UserInfoResult);
        assertTrue(UserInfoResult.isSuccessful());
        assertNotNull(UserInfoResult.getUserInfo());
    }

    private static String getTestUid() {
        return "test";
    }
}