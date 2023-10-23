本项目参考<https://github.com/SciDAS/nextflow-api>给出nextflow api的java实现版本。



### API Endpoints

| Endpoint                       | Method | Description                                 |
|--------------------------------|--------|---------------------------------------------|
| `/api/workflows`               | GET    | List all workflow instances                 |
| `/api/workflows`               | POST   | Create a workflow instance                  |
| `/api/workflows/{id}`          | GET    | Get a workflow instance                     |
| `/api/workflows/{id}`          | POST   | Update a workflow instance                  |
| `/api/workflows/{id}`          | DELETE | Delete a workflow instance                  |
| `/api/workflows/{id}/upload`   | POST   | Upload input files to a workflow instance   |
| `/api/workflows/{id}/launch`   | POST   | Launch a workflow instance                  |
| `/api/workflows/{id}/log`      | GET    | Get the log of a workflow instance          |
| `/api/workflows/{id}/download` | GET    | Download the output data as a tarball       |
| `/api/tasks`                   | GET    | List all tasks                              |
| `/api/tasks`                   | POST   | Save a task (used by Nextflow)              |
