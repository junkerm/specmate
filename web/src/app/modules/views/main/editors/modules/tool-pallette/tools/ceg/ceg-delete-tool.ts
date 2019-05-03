import { CEGModel } from '../../../../../../../../model/CEGModel';
import { DeleteToolBase } from '../delete-tool-base';

export class CEGDeleteTool extends DeleteToolBase {
    protected modelType: { className: string; } = CEGModel;
}
