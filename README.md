# What is GIT used for?

In this course, unless otherwise explicitly stated, your exercises will be submitted via GitLab. The last commit pushed before the deadline to the **master** branch on GitLab is decisive for the evaluation, grading, and feedback. The feedback will be provided here, i.e., on GitLab in a **DESIGN or FINAL milestone** respectively. To access these milestones check out the toolbar entry on the left named “Tickets” in German or “Issues” in English. The achieved points will be published using Moodle.

**Do not change the name of the master branch**: it must be called master. Only data that is in the master branch before the deadline (therefore committed and pushed before the deadline) will be taken into account during the submission interviews and evaluation. Double check that you use the correct repository (i.e., the one relevant for this semester and this course).

# How do I get local access to this repository?

To work optimally with this repository, you should mirror it to your local workstation. To do this, use the `git clone <yourRepoUrl>` command. To get hold of the required repository URL scroll up till you see a blue button labeled Clone - click it. Select the URL provided by “Clone with HTTPS”. This should be similar to https://git01lab.cs.univie.ac.at/......

An alternative would be “Clone with SSH”. We typically only recommend it if you have already a bit of experience with Git and SSH. For example, because this would require you to create public and private keys for authentication reasons.  

**Problems with the certificates**: If you are experiencing problems cloning your Git repository and you are experiencing problems with certificate validation, a quick solution is to turn it off (as a last resort). You can use the following command: git config --global http.sslVerify false

For team assignments, the use of branches and branching based **source code management strategies** is recommended. The assignment provides tips and recommendations on this area. If you want to learn about branches in a relaxed tutorial environment, we recommend to check out https://try.github.io/

# How do I use this repository?

Clone this repository as indicated above. Then you can interact with it based on standard git commands, such as, `git add`, `commit`, `push`, `checkout` etc. To do so you will need to specify your name and email address after the initial clone. This information is subsequently automatically used during each commit. Use the following commands to do so:

> `git config --global user.name "My name"`

> `git config --global user.email a123456@univie.ac.at`

Use your **real name** (i.e., not a nickname or an abbreviation) and your official **university mail address** (mandatory). Further, we recommend that you and your team members organize their efforts (e.g., work packages and their assignment) but also critical communication (e.g., if team members are unresponsive) here in GitLab using Git issues.

# How do I get assistance?

* If you encounter problems directly related to the assignment, please post in the **[Moodle Discussion Forum - Practical Assignment](https://moodle.univie.ac.at/mod/forum/view.php?id=10356964)**, as it may also be of interest to other colleagues.
* For implementation/coding problems, please open an issue in the GitLab Issue Tracker of this GitLab project and use the issue to document your own efforts while working towards a solution. In case you get stuck, ask our tutors for assistance by mentioning their handles in a comment to the issue. The SE2 tutor is:
  * Robert Sama, GitLab handle: @roberts95
* If you have specific questions regarding grading or team issues, please also open a GitLab Issue in this GitLab project and assign it to your team's supervisor. Your **team supervisor** can be identified based on your team id which is also visible in your GitLab Team Project name. The team id consists of 4 digits and the first two digits encode your supervisor:
  * Teams starting with `01` (e.g., team `0104`) are supervised by Amirali Amiri, GitLab handle: @amiralia57
  * Teams starting with `02` (e.g., team `0206`) are supervised by Konstantinos Plakidas, GitLab handle: @plakidask72
  * Teams starting with `03` (e.g., team `0309`) are supervised by Evangelos Ntentos, GitLab handle: @ntentose84
  * Teams starting with `04` (e.g., team `0403`) are supervised by Stephen Warnett, GitLab handle: @stephenw64
* As a last resort you can contact the course supervisors directly via [email](mailto:se2@swa.univie.ac.at).

 

# Which functions should not be used?

GitLab is a powerful software system that allows you to customize numerous settings and choose from various features. We would advise you to treat these settings and features with **respect and care**. For example, by simply clicking on each button, ignoring warnings etc. one could delete this repository's master branch for good (no we can’t restore it either). So: Think before you click!
