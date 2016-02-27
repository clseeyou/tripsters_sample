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

public class NetRequestTest extends AndroidTestCase {

    private static final String TEST_UID = "11387";
    private static final String TEST_QID = "1663";

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
        NetResult netResult = NetRequest.sendQuestionById(getContext(), TEST_UID, "question", null, "泰国", "33,34", null, null, null, null);
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testGetAnswer() throws Exception {
        AnswerList tagList = NetRequest.getAnswer(getContext(), TEST_QID, 1, Constants.PAGE_COUNT);
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
        QuestionResult questionResult = NetRequest.getQuestionDetail(getContext(), TEST_QID);
        assertNotNull(questionResult);
        assertTrue(questionResult.isSuccessful());
        assertNotNull(questionResult.getQuestion());
    }

    public void testSendAnswerById() throws Exception {
        NetResult netResult = NetRequest.sendAnswerById(getContext(), TEST_UID, "answer", null, null, null, TEST_QID, null);
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testSendReAnswerById() throws Exception {
        NetResult netResult = NetRequest.sendReAnswerById(getContext(), TEST_UID, "reanswer", null, null, null, TEST_QID, null, TEST_UID);
        assertNotNull(netResult);
        assertTrue(netResult.isSuccessful());
    }

    public void testLogin() throws Exception {
        UserInfoResult UserInfoResult = NetRequest.login(getContext(), "1", "android", "http://a.hiphotos.baidu.com/baike/w%3D268/sign=b63699f3a8773912c4268267c0188675/10dfa9ec8a13632722e51d7d908fa0ec08fac770.jpg", Gender.MALE.getValue(), null);
        assertNotNull(UserInfoResult);
        assertTrue(UserInfoResult.isSuccessful());
        assertNotNull(UserInfoResult.getUserInfo());
    }
}