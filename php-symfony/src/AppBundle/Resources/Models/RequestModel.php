<?php
/**
 * Request model
 * General requests collection
 * GET
 * TradeShift server response is xml
 *
 * @param array $tokenSession
 */

namespace AppBundle\Resources\Models;

use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\HttpKernel\Exception\HttpException;

use AppBundle\Resources\Models\SecurityModel;
use AppBundle\Resources\Models\XmlParser;

class RequestModel
{

    protected $token;

    /**
     * @param array $tokenSession
     */
    public function __construct(array $tokenSession)
    {
        $this->setToken( $tokenSession );
    }

    /**
     * @return mixed
     */
    public function getToken()
    {
        return $this->token;
    }


    /**
     * @param array $tokenArray | stored in session var 'token'
     * @return $this
     */
    public function setToken(array $tokenArray)
    {
        $this->token = $tokenArray;
        return $this;
    }

    /**
     * @return mixed
     */
    public function getAccessToken()
    {
        return $this->getToken()['access_token'];
    }

    /**
     * Build main parameters for curl command
     *
     * @param $url
     * @return resource | curl
     */
    private function buildCurl($url)
    {
        $restApiUrl = SecurityModel::TRADESHIFT_REST_API_URL . $url;

        $additionalHeaders = [
            'Authorization: '.SecurityModel::HEADER_AUTHORIZATION_REQUEST_TYPE.' '. $this->getAccessToken()
        ];

        $ch = curl_init($restApiUrl);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $additionalHeaders);
        curl_setopt($ch, CURLOPT_HEADER, false);
        curl_setopt($ch, CURLOPT_TIMEOUT, 30);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        return $ch;
    }


    /**
     * GET request to TS server
     *
     * @param $url
     * @param $type | string "array" or "json"
     * @return array|string
     */
    public function getData($url, $type = 'array')
    {
        $ch = $this->buildCurl($url);

        $tsResponse = curl_exec($ch);
        $curlResponse= curl_getinfo($ch);

        if ( $curlResponse['http_code'] != 200)
        {
            throw new HttpException ($curlResponse['http_code'], 'Curl error: ' . curl_error($ch) );
        }

        curl_close($ch);

        $parser = new XmlParser($tsResponse);

       switch ($type) {
           case 'array':
               return $parser->asArray();

           case 'json':
               return $parser->asJson();
       }
    }
}