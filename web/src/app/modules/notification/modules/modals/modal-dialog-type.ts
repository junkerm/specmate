export class Dialogtype {
    readonly hasSave: boolean;
    readonly hasForeward: boolean;
    readonly hasBackward: boolean;
    readonly hasShowLog: boolean;

    readonly acceptText: string;
    readonly hasAccept: boolean;
    readonly dismissText: string;
    readonly hasDismiss: boolean;

    readonly title: string;
    readonly message: string;

    public constructor(title: string, message: string, acceptText = 'ok', dismissText = 'cancel',
                    hasSave = false, hasShowLog = false, hasForeward = false, hasBackward = false) {
        this.title = title ? title : '';
        this.message = message ? message : '';

        this.acceptText = acceptText ? acceptText : '';
        this.hasAccept = (this.acceptText != '');

        this.dismissText = dismissText ? dismissText : '';
        this.hasDismiss = (this.dismissText != '');

        this.hasSave = hasSave;
        this.hasShowLog = hasShowLog;
        this.hasForeward = hasForeward;
        this.hasBackward = hasBackward;
    }


    public static errorDialog(message: string) {
        return new Dialogtype('Error', message, '', '', false, true, true, true);
    }

    public static okDialog(title: string, message: string) {
        return new Dialogtype(title, message, '', 'ok');
    }

    public static okCancelDialog(title: string, message: string) {
        return new Dialogtype(title, message, 'ok', 'cancel');
    }

    public static unsavedChangesDialog(message: string, withCancel: boolean) {
        return new Dialogtype('ConfirmationRequired', message, 'discard', withCancel ? 'cancel' : '', true);
    }

    static readonly DEFAULT = new Dialogtype('Default Title', 'Default Message');
}
