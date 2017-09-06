import { CEGNode } from "../../../../model/CEGNode";
import { CEGConnection } from "../../../../model/CEGConnection";
import { Config } from "../../../../config/config";
import { SpecmateDataService } from "../../../../services/data/specmate-data.service";
import { NodeTool } from "./node-tool";
import { CEGEffectNode } from "../../../../model/CEGEffectNode";

export class EffectNodeTool extends NodeTool {
    name: string = "Add Effect Node";
    icon: string = "plus";

    get newNode(): CEGNode {
        return new CEGEffectNode();
    }
}