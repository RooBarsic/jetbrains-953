package com.company.lib.imple;


import com.company.lib.api.LastCommonCommitsFinder;
import com.company.lib.api.LastCommonCommitsFinderFactory;
import com.company.vcs.imple.VcsRepositoryImpGithub;

/**
 * Implementation of LastCommonCommitsFinderFactory Interface
 *
 * @author Farrukh Karimov
 */
public class LastCommonCommitsFinderFactoryImpl implements LastCommonCommitsFinderFactory {
    @Override
    public LastCommonCommitsFinder create(String owner, String repo, String token) {
        return new LastCommonCommitsFinderImpl(new VcsRepositoryImpGithub(repo, owner, token));
    }
}
