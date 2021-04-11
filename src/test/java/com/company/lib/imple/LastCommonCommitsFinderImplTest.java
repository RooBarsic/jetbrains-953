package com.company.lib.imple;

import com.company.vcs.api.VcsRepository;
import com.company.vcs.imple.VcsRepositoryImpGithub;
import org.junit.Test;

import java.io.IOException;


public class LastCommonCommitsFinderImplTest {
    final String REPOSITORY_NAME;
    final String REPOSITORY_OWNER;
    final String REPOSITORY_TOKEN;

    public LastCommonCommitsFinderImplTest() {
        REPOSITORY_NAME = System.getenv("TESTING_REPOSITORY_NAME");
        REPOSITORY_OWNER = System.getenv("TESTING_REPOSITORY_OWNER");
        REPOSITORY_TOKEN = System.getenv("TESTING_REPOSITORY_TOKEN");
    }

    @Test(expected = Exception.class)
    public void canNotLoadAccessUndefinedBranch() throws IOException {
        final VcsRepository vcsRepository = new VcsRepositoryImpGithub(REPOSITORY_NAME, REPOSITORY_OWNER, REPOSITORY_TOKEN);

        vcsRepository.getAllBranchCommits("REPOSITORY_BRANCH");
    }
}
