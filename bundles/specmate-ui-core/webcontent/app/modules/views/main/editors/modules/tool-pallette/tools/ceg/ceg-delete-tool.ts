import { DeleteToolBase } from '../delete-tool-base';
import { CEGModel } from '../../../../../../../../model/CEGModel';

export class CEGDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = CEGModel;
}
