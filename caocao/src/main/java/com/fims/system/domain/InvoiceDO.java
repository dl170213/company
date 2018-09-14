package com.fims.system.domain;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
public class InvoiceDO
{
    private String invoiceNumber ;
    private String invoicedate ;
    private String number ;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "InvoiceDO{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", invoicedate=" + invoicedate +
                ", number='" + number + '\'' +
                '}';
    }
}
