import { CEGNode } from '../../../../model/CEGNode';
import { NodeTool } from './node-tool';
import { CEGCauseNode } from '../../../../model/CEGCauseNode';

export class CauseNodeTool extends NodeTool {

    name: string = 'Add Cause Node';
    icon: string = 'plus';

    get newNode(): CEGNode {
        return new CEGCauseNode();
    }
}
