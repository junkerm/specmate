/**
 * Adding @UISafe() to a getter delays changes to the underlying value until the next update cycle.
 * This prevents ExpressionChangedAfterItHasBeenCheckedErrors
 */
export function UISafe(): MethodDecorator {
    return function(target: any, propertyKey: string| symbol, descriptor: PropertyDescriptor) {
        let originalMethod = descriptor.get;
        if (!originalMethod) {
            throw new Error('@UISafe can only decorate getters.');
        }
        if (!descriptor.configurable) {
            throw new Error('@UISafe target must be configurable.');
        }
        let oldValue: any = undefined;
        let firstCall = true;
        descriptor.get = function() {
            let context = this;
            let args = arguments;
            let newValue = originalMethod.apply(context, args);
            if (firstCall) {
                oldValue = newValue;
                firstCall = false;
            }
            if (oldValue !== newValue) {
                setTimeout(() => {
                    oldValue = newValue;
                });
            }
            return oldValue;
        };
        return descriptor;
    };
}
