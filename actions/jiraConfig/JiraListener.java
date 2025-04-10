package jiraConfig;

import commons.GlobalConstants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class JiraListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        boolean isLogIssue = result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(JiraCreateIssue.class).isCreateIssue();
        if (isLogIssue) {
            JiraServiceProvider JiraServiceProvider = new JiraServiceProvider
                    (GlobalConstants.JIRA_URL, GlobalConstants.JIRA_USERNAME, GlobalConstants.JIRA_API_TOKEN, GlobalConstants.JIRA_PROJECT_KEY);
            String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage() + "\n";
            issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
            String issueSummary = result.getMethod().getConstructorOrMethod().getMethod()
                    .getName() + " Failed in Automation Testing";
            JiraServiceProvider.createJiraIssue("Bug", issueSummary, issueDescription);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
    }
}
