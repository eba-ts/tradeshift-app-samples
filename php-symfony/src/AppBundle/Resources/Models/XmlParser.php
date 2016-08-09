<?php
/**
 *  XML parser
 * to parse TS response
 */

namespace AppBundle\Resources\Models;


use Symfony\Component\Config\Definition\Exception\Exception;
use AppBundle\Resources\Models\XML2Array;

class XmlParser
{

    private $tsResponse;

    public function __construct($tsResponse)
    {
        if (!$tsResponse)
        {
            throw new Exception ('TS response is not passed to the constructor');
        }
        $this->tsResponse = $tsResponse;
    }

    /**
     * Converts XML to Array
     *
     * @return mixed
     */
    public function xmlToArray()
    {
        return XML2Array::createArray($this->tsResponse);
    }

    /**
     * Parser result converts to Json
     *
     * @return string
     */
    public function asJson()
    {
        return json_encode($this->xmlToArray());
    }

    /**
     * Parser result returned as array
     *
     * @return array
     */
    public function asArray()
    {
        return $this->xmlToArray();
    }

}