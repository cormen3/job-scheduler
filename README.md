# job-scheduler

If you have a repetitive task in your Android app, you need to consider that activities and services can be terminated by the Android system to free up resources. Therefore you can not rely on standard Java schedule like the TimerTasks class.


The Android 5.0 Lollipop (API 21) release introduces a job scheduler API via the JobScheduler class. This API allows to batch jobs when the device has more resources available. In general this API can be used to schedule everything that is not time critical for the user.


Here are example when you would use this job scheduler:
•	Tasks that should be done once the device is connect to a power supply
•	Tasks that require network access or a Wi-Fi connection.
•	Task that are not critical or user facing
•	Tasks that should be running on a regular basis as batch where the timing is not critical

