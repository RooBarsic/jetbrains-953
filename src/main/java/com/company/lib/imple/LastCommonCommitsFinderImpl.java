package com.company.lib.imple;

import com.company.lib.api.LastCommonCommitsFinder;
import com.company.vcs.api.VcsRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of LastCommonCommitsFinder Interface
 *
 * @author Farrukh Karimov
 */
public class LastCommonCommitsFinderImpl implements LastCommonCommitsFinder {
    private final VcsRepository vcsRepository;
    private final Map<String, List<String>> commitsShaListByBranchName;

    LastCommonCommitsFinderImpl(@NotNull final VcsRepository vcsRepositoryImpl) {
        vcsRepository = vcsRepositoryImpl;
        commitsShaListByBranchName = new HashMap<>();
    }

    /**
     * Finds SHAs of last commits that are reachable from both
     * branchA and branchB
     *
     * @param branchA   branch name (e.g. "main")
     * @param branchB   branch name (e.g. "dev")
     * @return  a collection of SHAs of last common commits
     * @throws IOException  if any error occurs
     */
    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {

        if (vcsRepository.isBranchExist(branchA) && vcsRepository.isBranchExist(branchB)) {

            try {
                final Set<String> branchACommitsSet = new HashSet<>(fetchBranch(branchA));
                final List<String> branchBCommitsList = new LinkedList<>(fetchBranch(branchB));

                final List<String> lastCommonCommitsSha = new LinkedList<>();
                for (final String branchBCommitSha : branchBCommitsList) {
                    if (branchACommitsSet.contains(branchBCommitSha)) {
                        lastCommonCommitsSha.add(branchBCommitSha);
                    }
                }

                return lastCommonCommitsSha;
            } catch (Exception ignored) {
                throw new IOException();
            }
        }
        return Collections.emptyList();
    }

    /**
     * method for loading and caching branch commits
     * @param branchName
     * @return list of commits sha
     * @throws IOException
     */
    private List<String> fetchBranch(@NotNull final String branchName) throws IOException {
        if (!commitsShaListByBranchName.containsKey(branchName)) {
            final List<String> branchCommitsList = new LinkedList<>(vcsRepository.getAllBranchCommits(branchName));
            commitsShaListByBranchName.put(branchName, branchCommitsList);
        } else {
            final String lastBranchCommitSha = commitsShaListByBranchName.get(branchName).get(commitsShaListByBranchName.get(branchName).size() - 1);
            final List<String> newBranchCommitsList = new LinkedList<>(vcsRepository.getNewCommitsList(branchName, lastBranchCommitSha));
            commitsShaListByBranchName
                    .get(branchName)
                    .addAll(newBranchCommitsList);
        }
        return commitsShaListByBranchName.get(branchName);
    }
}
