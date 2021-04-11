package com.company.vcs.imple;

import com.company.vcs.api.VcsRepository;
import org.junit.Test;

import static org.junit.Assert.fail;

public class VcsRepositoryImpGithubTest {
    final String REPOSITORY_NAME;
    final String REPOSITORY_OWNER;
    final String REPOSITORY_TOKEN;
    final String REPOSITORY_BRANCH;

    public VcsRepositoryImpGithubTest() {
        REPOSITORY_NAME = System.getenv("TESTING_REPOSITORY_NAME");
        REPOSITORY_OWNER = System.getenv("TESTING_REPOSITORY_OWNER");
        REPOSITORY_TOKEN = System.getenv("TESTING_REPOSITORY_TOKEN");
        REPOSITORY_BRANCH = System.getenv("TESTING_REPOSITORY_BRANCH");
    }

    @Test
    public void canAccessCurrentProjectRepository() {
        final VcsRepository vcsRepository = new VcsRepositoryImpGithub(REPOSITORY_NAME, REPOSITORY_OWNER, REPOSITORY_TOKEN);

        try {
            vcsRepository.getAllBranchCommits(REPOSITORY_BRANCH);
        } catch (Exception ignored) {
            fail("Should not have thrown any exception");
        }
    }
}
