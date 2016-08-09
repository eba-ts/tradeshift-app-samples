<?php
/**
 *  Main TradeShift server REST API requests
 */

namespace AppBundle\Resources\Models;

use AppBundle\Resources\Models\RequestModel;
use AppBundle\Controller\SecurityController;
use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\HttpKernel\Exception\HttpException;

class TsRestAPI
{
    CONST USER_INFO_API_URL = "/rest/external/account/info/user";
    CONST ACCOUNT_INFO_API_URL = "/rest/external/account/info";
    CONST DOCUMENTS_LIST_API_URL = "/rest/external/documents";

    CONST ERROR_MESSAGE = 'You are not authorized. Login into Tradeshift account to use this App';

    public function getLanguage()
    {
        $requestModel = $this->buildModelFactory();
        $requestGetResult = $requestModel->getData(static::USER_INFO_API_URL);

        return $requestGetResult['ts:UserAccountInfo']['ts:Language'];
    }

    /**
     * Get Company Info
     *
     * @return array|string
     */
    public function getCompanyInfo()
    {
        $requestModel = $this->buildModelFactory();
        $arr = $requestModel->getData(static::ACCOUNT_INFO_API_URL);

        $array = [
            'id' => $arr['ts:CompanyAccountInfo']['ts:CompanyAccountId'],
            'data' => [
                'name' => $arr['ts:CompanyAccountInfo']['ts:CompanyName'],
                'logo' => $arr['ts:CompanyAccountInfo']['ts:LogoURL'],
                'size' => $arr['ts:CompanyAccountInfo']['ts:Size'],
                'location' => $this->getAddress( $arr['ts:CompanyAccountInfo']['ts:AddressLines'] ),
                'industry' => $arr['ts:CompanyAccountInfo']['ts:Industry']
            ]
        ];
        return $array;
    }


    /**
     * Get TS Documents list
     *
     * @param $documentType | string "invoice", "order"
     * @param $limit    | integer
     * @param $pageNumber   | integer
     * @return array|string
     */
    public function getDocumentList($documentType, $limit=null, $pageNumber=null)
    {
        $requestModel = $this->buildModelFactory();
        $arr = $requestModel->getData(static::DOCUMENTS_LIST_API_URL. '?type=' .$documentType.
                                '&limit='. $limit .'&page=' .$pageNumber);

        $result = [];

        foreach ($arr['ts:DocumentList']['ts:Document']  as $documents)
        {
            //document currency and total
            $total=0;
            $currency='';
            foreach ($documents['ts:ItemInfos']['ts:ItemInfo'] as $info)
            {
                if ('document.total' == $info['attributes']['type']){
                    $total = $info['value'];
                }
                if ('document.currency' == $info['attributes']['type']){
                    $currency = $info['value'];
                }
            }

            $result[] = [
               'id'=> $documents['ts:ID'],
                'type' => $documents['ts:DocumentType']['attributes']['type'],
                'state' => $documents['ts:State'],
                'date' => $documents['ts:SentReceivedTimestamp'],
                'companyName' => $documents['ts:ReceiverCompanyName'],
                'total' => $total,
                'currency' => $currency

            ];
        }
        return $result;
    }

    /**
     * Builds request Model for use in API calls
     *
     * @return \AppBundle\Resources\Models\RequestModel
     */
    private function buildModelFactory()
    {
        $session = new Session();
        if (!$session->get('token'))
        {
            throw new HttpException(401,static::ERROR_MESSAGE);
        }
        return new RequestModel( $session->get('token') );
    }

    /**
     * Get Address string from Company info collection
     *
     * @param $array
     * @return string
     */
    private function getAddress($array)
    {
        $string = '';
        foreach ($array['ts:AddressLine'] as $data)
        {
            $string .= $data['value'] . ', ';
        }
        return $string;
    }

}