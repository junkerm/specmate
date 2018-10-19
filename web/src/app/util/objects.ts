import { Type } from './type';

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
            if (!source[name]) {
                continue;
            }
            actualTarget[name] = Objects.getFreshInstance(source[name]);
            if (Objects.isObject(source[name])) {
                Objects.clone(source[name], actualTarget[name]);
            } else if (Objects.isArray(source[name])) {
              for (let i = 0; i < source.length; i++) {
                actualTarget[name][i] = Objects.clone(source[name][i]);
              }
            } else {
              actualTarget[name] = source[name];
            }
        }
      return actualTarget;
    }

    /**
     * Get (flat) the fields that are different between two objects. It only compares values, and references flat.
     */
    public static changedFields(o1: any, o2: any): string[] {
        if (o1 === undefined || o2 === undefined) {
            return undefined;
        }
        if (!Type.is(o1, o2)) {
            throw new Error('Types do not match! Tried to get changed fields from unmatching types.');
        }

        const changedFields: string[] = [];
      for (const field in o1) {
        if (!Objects.isObject(o1[field])) {
          if (!Objects.fieldsEqualIgnoreBooleanStrings(o1[field], o2[field])) {
            changedFields.push(field);
          }
        } else if (Objects.isArray(o1[field])) {
          if (o1[field].length !== o2[field].length) {
            changedFields.push(field);
            continue;
          }
          for (let i = 0; i < o1[field].length; i++) {
            if (!Objects.fieldsEqualIgnoreBooleanStrings(o1[field][i], o2[field][i])) {
              changedFields.push(field);
              break;
            }
          }
        }
        if (!o2[field]) {
          changedFields.push(field);
        }
      }
      for (const field in o2) {
            if (!o1[field]) {
                changedFields.push(field);
            }
        }
        return changedFields;
    }

    private static fieldsEqualIgnoreBooleanStrings(p1: any, p2: any): boolean {
        if ((Objects.isBoolean(p1) && Objects.isStringBoolean(p2)) || (Objects.isStringBoolean(p1) && Objects.isBoolean(p2))) {
            return p1 + '' === p2 + '';
        }
        if (Objects.isObject(p1) && Objects.isObject(p2)) {
            return Objects.equals(p1, p2);
        }
        return p1 === p2;
    }

    private static isBoolean(p: any): boolean {
        return p === true || p === false;
    }

    private static isStringBoolean(p: any): boolean {
        return p === 'true' || p === 'false';
    }

    private static getFreshInstance(element: any) {
      if (Objects.isArray(element)) {
        return [];
      }
      if (Objects.isObject(element)) {
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
      if (!(o1 && o2)) {
        return false;
      }
      for (const name in o1) {
        const element1 = o1[name];
        const element2 = o2[name];

        const element1_type = typeof (element1);
        const element2_type = typeof (element2);

        if (!element1 || element1_type !== element2_type) {
            return false;
        } else if (element1_type !== 'object' && element1_type !== 'function') {
          if (element1 !== element2) {
            return false;
          }
        } else if (!this.equals(element1, element2)) {
          return false;
            
 }
