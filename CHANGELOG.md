# Changelog 

## Version 3.0.0 - 2021-10-14

- Removed all support for batches. Use datasets instead.
- Added UpdateDocumentOptions
- Added datasetId, groundTruth and retentionInDays to UpdateDocumentOptions

## Version 2.0.0 - 2021-08-31

- Bugfixes for List and Delete Options
- Added argument datasetId to ListDocumentsOptions 
- Added argument datasetId to DeleteDocumentsOptions
- Removed toList function from Options

## Version 1.2.0 - 2021-05-21

- Added createModel (POST /models)
- Added getModel (GET /models/:id)
- Added updateModel (PATCH /models/:id)
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
