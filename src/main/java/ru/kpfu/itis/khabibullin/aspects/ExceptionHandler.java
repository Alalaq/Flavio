/*
 * *
 *  Copyright (c) 2023.
 *  * @author Khabibullin Alisher (Alalaq)
 *  *
 *  * All rights are reserved by ITIS institute.
 *
 */

package ru.kpfu.itis.khabibullin.aspects;

import org.apache.log4j.Logger;

public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    public static void handleException(Exception ex) {
        logger.error("Exception occurred: ", ex);
    }
}
