export class Objects {

    public static clone(source: any, target?: any): any {

        if (source === target) {
            return;
        }

        let actualTarget = target;

        if (target === undefined) {
            actualTarget = Objects.getFreshInstance(source);
        }
        for (let name in source) {
            actualTarget[name] = Objects.getFreshInstance(source[name]);
            if(Objects.isObject(source[name])) {
                Objects.clone(source[name], actualTarget[name]);
            } else if(Objects.isArray(source[name])) {
                for(let i = 0; i < source.length; i++) {
                    actualTarget[name][i] = Objects.clone(source[name][i]);
                }
            } else {
                actualTarget[name] = source[name];
            }
        }
        return actualTarget;
    }

    private static getFreshInstance(element: any) {
        if (Objects.isArray(element)) {
            return [];
        }
        else if (Objects.isObject(element)) {
            return {};
        }
        return '';
    }

    private static isObject(element: any): boolean {
        return typeof (element) === 'object' || typeof (element) === 'function';
    }

    private static isArray(element: any) {
        return Array.isArray(element);
    }

    public static equals(o1: any, o2: any): boolean {
        if (o1 && o2) {
            for (let name in o1) {
                if (!o2[name] || typeof (o1[name]) !== typeof (o2[name])) {
                    return false;
                } else if (typeof (o1[name]) !== 'object' && typeof (o1[name]) !== 'function') {
                    if (o1[name] !== o2[name]) {
                        return false;
                    }
                } else {
                    if (!this.equals(o1[name], o2[name])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}