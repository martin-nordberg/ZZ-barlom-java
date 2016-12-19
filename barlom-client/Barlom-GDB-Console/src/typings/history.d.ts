
// Fudged from https://github.com/maiermic/todomvc-cycle-typescript/blob/master/typings/modules/history/index.d.ts

/// <reference path="../../node_modules/@cycle/history/lib/interfaces.d.ts"/>

declare module 'history' {
    module otherHistory {

        //import {History} from '@cycle/history'

      ///////

        type CreateHistoryDefault = CreateHistory<HistoryOptions, History>;

        type CreateHistory<TOptions extends HistoryOptions, TResult extends History> = (options?: TOptions) => TResult;

        interface HistoryOptions {
            getUserConfirmation?(message: string, callback: (confirmed: boolean) => void): void;
            queryKey?: boolean | string;
        }

        export const createHistory: CreateHistoryDefault;

        export const createHashHistory: CreateHistoryDefault;

        export const createMemoryHistory: CreateHistoryDefault;

    }

    export = otherHistory;
}