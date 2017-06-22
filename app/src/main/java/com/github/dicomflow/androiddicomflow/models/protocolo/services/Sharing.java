package com.github.dicomflow.androiddicomflow.models.protocolo.services;

import com.github.dicomflow.androiddicomflow.models.protocolo.Action;

/**
 * Created by ricardobarbosa on 15/06/17.
 */

public class Sharing extends Service {
    Sharing() {}

    protected static class PutAction extends Action {
        public PutAction() {
            super();
        }
        @Override
        public String getName() {
            return "PUT";
        }
    }

    protected static class ResultAction extends Action {
        public ResultAction() {
            super();
        }
        @Override
        public String getName() {
            return "RESULT";
        }
    }

    @Override
    public String getName() {
        return "DISCOVERY";
    }
    @Override
    public String getAction() {
        return null;
    }
}
