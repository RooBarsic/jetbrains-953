package com.company.lib.imple;

import com.company.vcs.api.VcsRepository;
import com.company.vcs.imple.VcsRepositoryImpGithub;
import org.junit.Test;

import java.io.IOException;


public class LastCommonCommitsFinderImplTest {
    final String REPOSITORY_NAME = "jetbrains-953";
    final String REPOSITORY_OWNER = "RooBarsic";
    final String REPOSITORY_TOKEN = "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";

    @Test(expected = Exception.class)
    public void canNotLoadAccessUndefinedBranch() throws IOException {
        final VcsRepository vcsRepository = new VcsRepositoryImpGithub(REPOSITORY_NAME, REPOSITORY_OWNER, REPOSITORY_TOKEN);

        vcsRepository.getAllBranchCommits("REPOSITORY_BRANCH");
    }
}
