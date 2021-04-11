package com.company;

import com.company.lib.api.LastCommonCommitsFinder;
import com.company.lib.imple.LastCommonCommitsFinderFactoryImpl;
import com.company.utils.RestUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello TeamCity");

        Scanner scanner = new Scanner(System.in);

        System.out.print("UserName : ");
        String userName = scanner.next();

        String token = "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";

        LastCommonCommitsFinderFactoryImpl lastCommonCommitsFinderFactory = new LastCommonCommitsFinderFactoryImpl();
        System.out.print("repo name : ");
        String repoName = scanner.next();
        LastCommonCommitsFinder lastCommonCommitsFinder = lastCommonCommitsFinderFactory.create(userName, repoName, token);

        while (true) {
            System.out.println("\nnew checking");
            System.out.print("branch A name : ");
            String branchA = scanner.next();
            System.out.print("branch B name : ");
            String branchB = scanner.next();

            try {
                List<String> commonCommitsList = (List<String>) lastCommonCommitsFinder.findLastCommonCommits(branchA, branchB);

                if (commonCommitsList == null)
                    continue;

                System.out.println("common commits list : ");
                for (final String commonCommit : commonCommitsList) {
                    System.out.println(commonCommit);
                }
                System.out.println("ans size = " + commonCommitsList.size());
            } catch (Exception ignored) {
                System.out.println(" some exception occurs");
            }
        }
    }
}
