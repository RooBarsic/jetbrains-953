package com.company.vcs.imple;

import com.company.utils.RestUtils;
import com.company.vcs.api.VcsRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;

public class VcsRepositoryImpGithub implements VcsRepository {
    private final int PAGE_SIZE = 100;
    private final String repositoryName;
    private final String repositoryOwner;
    private final String token;
    private final String BASE_URL = "https://api.github.com/repos";
    private final String ACCESS_TOKEN_ADDITION = "access_token";

    public VcsRepositoryImpGithub(@NotNull final String repositoryName,
                                  @NotNull final String repositoryOwner,
                                  @NotNull final String token) {
        this.repositoryName = repositoryName;
        this.repositoryOwner = repositoryOwner;
        this.token = token;
    }

    @Override
    public Collection<String> getAllBranchCommits(@NotNull final String branchName) throws IOException {
        final List<String> branchCommitsSha = new LinkedList<>();
        final String requestUrl = BASE_URL
                + "/" + repositoryOwner
                + "/" + repositoryName
                + "/commits"
                + "?sha=" + branchName
                + "&access_token=" + token;

        int curPageId = 1;
        Collection<String> curPageCommitsList;
        do {
            curPageCommitsList = getFixedCommitsList(requestUrl, curPageId);
            branchCommitsSha.addAll(curPageCommitsList);
            curPageId++;
        } while (curPageCommitsList.size() > 0);

        Collections.reverse(branchCommitsSha);
        return branchCommitsSha;
    }

    @Override
    public Collection<String> getNewCommitsList(@NotNull final String branchName, @NotNull final String lastLoadedCommitSha) throws IOException {
        final String lastLoadedCommitPublishingDate = getCommitPublishingDate(lastLoadedCommitSha);

        final List<String> newCommitsShaList = new LinkedList<>();
        final String requestUrl = BASE_URL
                + "/" + repositoryOwner
                + "/" + repositoryName
                + "/commits"
                + "?sha=" + branchName
                + "&since=" + lastLoadedCommitPublishingDate
                + "&access_token=" + token;

        int curPageId = 1;
        Collection<String> curPageCommitsList;
        do {
            curPageCommitsList = getFixedCommitsList(requestUrl, curPageId);
            newCommitsShaList.addAll(curPageCommitsList);
            curPageId++;
        } while (curPageCommitsList.size() > 0);

        newCommitsShaList.remove(newCommitsShaList.size() - 1);
        Collections.reverse(newCommitsShaList);
        return newCommitsShaList;

    }


    private String getCommitPublishingDate(@NotNull final String commitSha) throws IOException {
        final String requestUrl = BASE_URL
                + "/" + repositoryOwner
                + "/" + repositoryName
                + "/commits"
                + "?per_page=1"
                + "&sha=" + commitSha
                + "&page=1"
                + "&access_token=" + token;

        final String responseJson = RestUtils.httpsGETRequest(requestUrl);

        final JSONArray commitsInfoJsonArray = new JSONArray(responseJson);
        final JSONObject commitInfo = commitsInfoJsonArray.getJSONObject(0).getJSONObject("commit");
        final JSONObject committerInfo = commitInfo.getJSONObject("committer");
        final String commitPublishingDate = committerInfo.getString("date");

        return commitPublishingDate;

    }

    private Collection<String> getFixedCommitsList(@NotNull final String baseBranch, final int pageNumber) throws IOException {
        final String requestUrl = baseBranch
                + "&per_page=" + PAGE_SIZE
                + "&page=" + pageNumber;

        final String responseJson = RestUtils.httpsGETRequest(requestUrl);

        final JSONArray commitsInfoJsonArray = new JSONArray(responseJson);

        final List<String> commitsSha = new LinkedList<>();

        for (int i = 0; i < commitsInfoJsonArray.length(); i++) {
            final JSONObject jsonObject = commitsInfoJsonArray.getJSONObject(i);

            if (jsonObject.has("sha")) {
                final String commitSha = jsonObject.getString("sha");
                commitsSha.add(commitSha);
            }
        }

        return commitsSha;
    }

    @Override
    public boolean isBranchExist(@NotNull String branchName) {
        return true;
    }
}
