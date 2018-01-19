
lazy val bounded_mailbox_master = (project in file("."))
  .aggregate(
    common,
    exercise_000_initial_state,
    exercise_001_futures
 ).settings(CommonSettings.commonSettings: _*)

lazy val common = project.settings(CommonSettings.commonSettings: _*)

lazy val exercise_000_initial_state = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")

lazy val exercise_001_futures = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
       