import { ITool } from "./ITool";
import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/specmate-data.service";
import { NodeTool } from "./node-tool";
import { CEGCauseNode } from "../../../../model/CEGCauseNode";

export class CauseNodeTool extends NodeTool {
    
    name: string = "Add Cause Node";
    icon: string = "plus";

    get newNode(): CEGNode {
        return new CEGCauseNode();
    }
}