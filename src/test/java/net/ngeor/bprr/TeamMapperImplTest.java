package net.ngeor.bprr;

import net.ngeor.util.ResourceLoader;
import net.ngeor.util.ResourceLoaderImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TeamMapperImplTest {
    private TeamMapperImpl teamMapper;
    private ResourceLoader resourceLoader;

    @Before
    public void before() {
        resourceLoader = new ResourceLoaderImpl();
        teamMapper = new TeamMapperImpl(resourceLoader);
        teamMapper.put("ngeor", "DDV");
        teamMapper.put("user1", "ADV");
    }

    @Test
    public void shouldSetAuthor() {
        PullRequestModel model = new PullRequestModel();
        model.setAuthor("ngeor");

        // act
        teamMapper.assignTeams(model);

        // assert
        assertEquals("DDV", model.getAuthorTeam());
    }

    @Test
    public void shouldSetReviewers() {
        PullRequestModel model = new PullRequestModel();
        model.setReviewers(new String[]{"ngeor", "user1"});

        // act
        teamMapper.assignTeams(model);

        // assert
        assertArrayEquals(new String[]{"DDV", "ADV"}, model.getReviewerTeams());
    }
}
