package net.ngeor.bprr;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.ngeor.bitbucket.Repositories;
import net.ngeor.bitbucket.Repository;
import net.ngeor.http.JsonHttpClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests for {@link BitbucketClient}.
 */
@ExtendWith(MockitoExtension.class)
class BitbucketClientTest {
    @Mock
    private JsonHttpClient jsonHttpClient;

    private BitbucketClient bitbucketClient;

    @BeforeEach
    void beforeEach() {
        bitbucketClient = new BitbucketClient(jsonHttpClient, "acme");
    }

    /**
     * Unit tests for {@link BitbucketClient#getAllRepositories()}.
     */
    @Nested
    class GetAllRepositories {
        @Test
        void test() throws IOException {
            // arrange
            Repository firstRepository = new Repository().name("repo1");
            Repositories firstPage =
                new Repositories().values(Collections.singletonList(firstRepository)).next("http://second-page");

            Repository secondRepository = new Repository().name("repo2");
            Repositories secondPage     = new Repositories().values(Collections.singletonList(secondRepository));

            doReturn(firstPage)
                .when(jsonHttpClient)
                .read("https://api.bitbucket.org/2.0/repositories/acme", Repositories.class);
            doReturn(secondPage).when(jsonHttpClient).read("http://second-page", Repositories.class);

            // act
            List<Repository> allRepositories = bitbucketClient.getAllRepositories();

            // assert
            assertThat(allRepositories).containsExactly(firstRepository, secondRepository);
        }
    }
}
