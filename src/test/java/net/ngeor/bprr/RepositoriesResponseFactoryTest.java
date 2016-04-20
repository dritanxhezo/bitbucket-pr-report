package net.ngeor.bprr;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class RepositoriesResponseFactoryTest {
    @Test
    public void shouldParseJson() {
        String json = "{\n" +
                "\t\"pagelen\": 10,\n" +
                "\t\"values\": [{\n" +
                "\t\t\"scm\": \"git\",\n" +
                "\t\t\"website\": null,\n" +
                "\t\t\"has_wiki\": false,\n" +
                "\t\t\"name\": \"Build Suite\",\n" +
                "\t\t\"links\": {\n" +
                "\t\t\t\"watchers\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/watchers\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"branches\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/refs/branches\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"tags\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/refs/tags\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"commits\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/commits\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"clone\": [{\n" +
                "\t\t\t\t\"href\": \"https://ngeor@bitbucket.org/ngeor/build-suite.git\",\n" +
                "\t\t\t\t\"name\": \"https\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"href\": \"ssh://git@bitbucket.org/ngeor/build-suite.git\",\n" +
                "\t\t\t\t\"name\": \"ssh\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"self\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"html\": {\n" +
                "\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/build-suite\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/build-suite/avatar/32/\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hooks\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/hooks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"forks\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/forks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"downloads\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/downloads\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"pullrequests\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/pullrequests\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"fork_policy\": \"no_public_forks\",\n" +
                "\t\t\"uuid\": \"{5b120c07-40b7-4352-b796-9afba1887af3}\",\n" +
                "\t\t\"language\": \"javascript\",\n" +
                "\t\t\"created_on\": \"2014-12-01T14:30:18.775867+00:00\",\n" +
                "\t\t\"parent\": {\n" +
                "\t\t\t\"links\": {\n" +
                "\t\t\t\t\"self\": {\n" +
                "\t\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/demandware/build-suite-ant\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"html\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/demandware/build-suite-ant\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/demandware/build-suite-ant/avatar/32/\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"type\": \"repository\",\n" +
                "\t\t\t\"name\": \"Build Suite Ant\",\n" +
                "\t\t\t\"full_name\": \"demandware/build-suite-ant\",\n" +
                "\t\t\t\"uuid\": \"{4db1b469-3ce3-4370-8914-a6269e344c74}\"\n" +
                "\t\t},\n" +
                "\t\t\"full_name\": \"ngeor/build-suite\",\n" +
                "\t\t\"has_issues\": false,\n" +
                "\t\t\"owner\": {\n" +
                "\t\t\t\"username\": \"ngeor\",\n" +
                "\t\t\t\"display_name\": \"Nikolaos Georgiou\",\n" +
                "\t\t\t\"type\": \"user\",\n" +
                "\t\t\t\"uuid\": \"{b035c98c-8f83-49d0-8770-95c80cc24dc5}\",\n" +
                "\t\t\t\"links\": {\n" +
                "\t\t\t\t\"self\": {\n" +
                "\t\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/users/ngeor\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"html\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/account/ngeor/avatar/32/\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"updated_on\": \"2014-12-01T15:01:07.789578+00:00\",\n" +
                "\t\t\"size\": 98147735,\n" +
                "\t\t\"type\": \"repository\",\n" +
                "\t\t\"is_private\": true,\n" +
                "\t\t\"description\": \"Forking to resolve missing parenthesis bug in file bundling\"\n" +
                "\t}, {\n" +
                "\t\t\"scm\": \"git\",\n" +
                "\t\t\"website\": null,\n" +
                "\t\t\"has_wiki\": false,\n" +
                "\t\t\"name\": \"GruntTasks\",\n" +
                "\t\t\"links\": {\n" +
                "\t\t\t\"watchers\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/watchers\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"branches\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/refs/branches\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"tags\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/refs/tags\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"commits\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/commits\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"clone\": [{\n" +
                "\t\t\t\t\"href\": \"https://ngeor@bitbucket.org/ngeor/grunttasks.git\",\n" +
                "\t\t\t\t\"name\": \"https\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"href\": \"ssh://git@bitbucket.org/ngeor/grunttasks.git\",\n" +
                "\t\t\t\t\"name\": \"ssh\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"self\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"html\": {\n" +
                "\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/grunttasks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/grunttasks/avatar/32/\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hooks\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/hooks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"forks\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/forks\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"downloads\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/downloads\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"pullrequests\": {\n" +
                "\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/ngeor/grunttasks/pullrequests\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"fork_policy\": \"allow_forks\",\n" +
                "\t\t\"uuid\": \"{78f5e944-75b0-471f-b7d0-02bdbe5694ff}\",\n" +
                "\t\t\"language\": \"\",\n" +
                "\t\t\"created_on\": \"2014-12-09T10:03:07.835605+00:00\",\n" +
                "\t\t\"parent\": {\n" +
                "\t\t\t\"links\": {\n" +
                "\t\t\t\t\"self\": {\n" +
                "\t\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/repositories/jmatos/grunttasks\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"html\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/jmatos/grunttasks\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/jmatos/grunttasks/avatar/32/\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t\"type\": \"repository\",\n" +
                "\t\t\t\"name\": \"GruntTasks\",\n" +
                "\t\t\t\"full_name\": \"jmatos/grunttasks\",\n" +
                "\t\t\t\"uuid\": \"{88e76433-3515-4168-aa8b-a41e4b4669d6}\"\n" +
                "\t\t},\n" +
                "\t\t\"full_name\": \"ngeor/grunttasks\",\n" +
                "\t\t\"has_issues\": false,\n" +
                "\t\t\"owner\": {\n" +
                "\t\t\t\"username\": \"ngeor\",\n" +
                "\t\t\t\"display_name\": \"Nikolaos Georgiou\",\n" +
                "\t\t\t\"type\": \"user\",\n" +
                "\t\t\t\"uuid\": \"{b035c98c-8f83-49d0-8770-95c80cc24dc5}\",\n" +
                "\t\t\t\"links\": {\n" +
                "\t\t\t\t\"self\": {\n" +
                "\t\t\t\t\t\"href\": \"https://api.bitbucket.org/2.0/users/ngeor\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"html\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/ngeor/\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"avatar\": {\n" +
                "\t\t\t\t\t\"href\": \"https://bitbucket.org/account/ngeor/avatar/32/\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"updated_on\": \"2015-06-30T08:34:15.735629+00:00\",\n" +
                "\t\t\"size\": 11428379,\n" +
                "\t\t\"type\": \"repository\",\n" +
                "\t\t\"is_private\": false,\n" +
                "\t\t\"description\": \"\"\n" +
                "\t}],\n" +
                "\t\"page\": 1,\n" +
                "\t\"size\": 8\n" +
                "}";
        RepositoriesResponseFactory factory = new RepositoriesResponseFactory();
        StringReader reader = new StringReader(json);
        RepositoriesResponse response = factory.parse(reader);
        Assert.assertNotNull(response);

        RepositoriesResponse.Repository[] values = response.getValues();
        Assert.assertEquals(2, values.length);

        RepositoriesResponse.Repository firstRepo = values[0];
        Assert.assertEquals("Build Suite", firstRepo.getName());
        Assert.assertEquals("https://api.bitbucket.org/2.0/repositories/ngeor/build-suite/pullrequests", firstRepo.getLinks().getPullRequests().getHref());
        Assert.assertEquals("ngeor/build-suite", firstRepo.getFullName());
    }
}
