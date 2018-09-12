import { NgModule } from '@angular/core';
import { DuplicateIOVariableValidator } from './ceg/duplicate-io-variable-validator';
import { DuplicateNodeValidator } from './ceg/duplicate-node-validator';
import { EmptyModelValidator } from './ceg/empty-model-validator';
import { SingleNodesValidator } from './ceg/single-nodes-validator';
import { DecisionMultipleOutgoingConnectionsValidator } from './process/decision-multiple-outgoing-connections-validator';
import { EndNodeNoOutgoingConnectionValidator } from './process/end-node-no-outgoing-connection-validator';
import { EndNodeValidator } from './process/end-node-validator';
import { HasStepsValidator } from './process/has-steps-validator';
import { MissingConditionValidator } from './process/missing-condition-validator';
import { NodeNoIncomingValidator } from './process/node-no-incoming-validator';
import { NodeNoOutgoingValidator } from './process/node-no-outgoing-validator';
import { NodeSingleOutgoingConnectionValidator } from './process/node-single-outgoing-connection-validator';
import { StartNodeNoIncomingConnectionValidator } from './process/start-node-no-incoming-connection-validator';
import { StartNodeValidator } from './process/start-node-validator';

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES (IN THIS CASE: VALIDATORS)
    DuplicateNodeValidator,
    DuplicateIOVariableValidator,
    EmptyModelValidator,
    SingleNodesValidator,
    EndNodeValidator,
    EndNodeNoOutgoingConnectionValidator,
    HasStepsValidator,
    NodeSingleOutgoingConnectionValidator,
    MissingConditionValidator,
    NodeNoIncomingValidator,
    NodeNoOutgoingValidator,
    StartNodeValidator,
    StartNodeNoIncomingConnectionValidator,
    DecisionMultipleOutgoingConnectionsValidator
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})
export class ValidationModule { }
