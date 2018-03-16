import { NgModule } from '@angular/core';
import { DuplicateNodeValidator } from './ceg/duplicate-node-validator';
import { DuplicateIOVariableValidator } from './ceg/duplicate-io-variable-validator';
import { EmptyModelValidator } from './ceg/empty-model-validator';
import { SingleNodesValidator } from './ceg/single-nodes-validator';
import { EndNodeValidator } from './process/end-node-validator';
import { HasStepsValidator } from './process/has-steps-validator';
import { MissingConditionValidator } from './process/missing-condition-validator';
import { NodeNoIncomingValidator } from './process/node-no-incoming-validator';
import { NodeNoOutgoingValidator } from './process/node-no-outgoing-validator';
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
    // SERVICES
    DuplicateNodeValidator,
    DuplicateIOVariableValidator,
    EmptyModelValidator,
    SingleNodesValidator,
    EndNodeValidator,
    HasStepsValidator,
    MissingConditionValidator,
    NodeNoIncomingValidator,
    NodeNoOutgoingValidator,
    StartNodeValidator
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})
export class ValidationModule { }
