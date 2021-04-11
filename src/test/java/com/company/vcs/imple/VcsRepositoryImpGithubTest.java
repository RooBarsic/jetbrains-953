package com.company.vcs.imple;

import com.company.vcs.api.VcsRepository;
import org.junit.Test;

import static org.junit.Assert.fail;

public class VcsRepositoryImpGithubTest {
    final String REPOSITORY_NAME = "jetbrains-953";
    final String REPOSITORY_OWNER = "RooBarsic";
    final String REPOSITORY_TOKEN = "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";
    final String REPOSITORY_BRANCH = "master";

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
