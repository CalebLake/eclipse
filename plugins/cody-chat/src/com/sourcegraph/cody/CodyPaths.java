package com.sourcegraph.cody;

import dev.dirs.ProjectDirectories;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodyPaths {

  public static String serverTracePath() {
    var override = System.getProperty("cody.debug-trace-path");
    if (override != null) {
      return override;
    }
    return Paths.get(projectDirectories().dataLocalDir).resolve("server-debug.json").toString();
  }

  public static String clientTracePath() {
    var override = System.getProperty("cody-agent.trace-path");
    if (override != null) {
      return override;
    }
    return Paths.get(projectDirectories().dataLocalDir).resolve("client-debug.json").toString();
  }

  public static ProjectDirectories projectDirectories() {
    return ProjectDirectories.from("com.sourcegraph", "Sourcegraph", "CodyEclipse");
  }

  // The project directory that the cody app (agent) uses for its config files
  public static Path codyDir() {
    return Paths.get(ProjectDirectories.from("", "", "Cody-nodejs").preferenceDir);
  }

  public static Path codyWebviewDir() {
    return codyDir().resolve("dist").resolve("webviews");
  }

  public static Path dataDir() {
    return Paths.get(projectDirectories().dataDir);
  }

  public static Path tempDir() throws IOException {
    // create a new temporary directory
    return Files.createTempDirectory("temp-");
  }

  public static Path agentScript(CodyResources resources) throws IOException {
    String userProvidedAgentScript = System.getProperty("cody.agent-script-path");
    if (userProvidedAgentScript != null) {
      return Paths.get(userProvidedAgentScript);
    }
    return resources.getAgentEntry();
  }
}
