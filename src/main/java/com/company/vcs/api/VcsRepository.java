package com.company.vcs.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 *
 *
 * @author Farrukh Karimov
 */
public interface VcsRepository {

    Collection<String> getAllBranchCommits(@NotNull final String branchName) throws IOException;

    boolean isBranchExist(@NotNull final String branchName);

    Collection<String> getNewCommitsList(@NotNull final String branchName, @NotNull final String lastLoadedCommitSha) throws IOException;

}
