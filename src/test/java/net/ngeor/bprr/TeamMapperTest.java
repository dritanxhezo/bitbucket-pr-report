package net.ngeor.bprr;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TeamMapperTest {
    private DefaultTeamMapper teamMapper;

    @Before
    public void before() {
        teamMapper = new DefaultTeamMapper();
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
