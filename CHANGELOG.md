# Changelog 

## Version 1.2.0 - 2021-05-19

- Added createModel (POST /models)
- Added updateBatch (PATCH /batches/:id)
- Added updateAppClient (PATCH /appClients/:id)

## Version 1.1.0 - 2021-05-04

- Added createAppClient (POST /appClients)
- Added listAppClients (GET /appClients)
- Added deleteAppClient (DELETE /appClients/:id)
- Added deleteAsset (DELETE /assets/:id)
- Added listBatches (GET /batches)
- Added deleteBatch (DELETE /batches/:id)
- Updated deleteDocuments (DELETE /documents) to use options including batchId, consentId, maxResults and nextToken 
- Added listLogs (GET /logs)
- Added deleteSecret (DELETE /secrets/:id)
- Updated CreateTransitionOptions to use dedicated types for DockerTransitionParameters and ManualTransitionParameters
- Added completedConfig to CreateWorkflowOptions 
- Updated CreateWorkflowOptions to use dedicated types for WorkflowErrorConfig and WorkflowCompletedConfig
