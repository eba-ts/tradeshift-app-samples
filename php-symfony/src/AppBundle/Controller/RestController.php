<?php
/**
 * REST controller
 * builds REST API for Simple App front-end
 */

namespace AppBundle\Controller;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Resources\Models\TsRestAPI;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Session\Session;

class RestController extends FOSRestController
{
    /**
     * Get locale
     * GET /api/locale
     */
    public function getLocaleAction()
    {
        $locale = new TsRestAPI();
        return $locale->getLanguage();
    }

    /**
     * Company info
     * GET /api/account/info
     *
     * @return json Object
     */
    public function getCompanyInfoAction()
    {
        $accountInfo = new TsRestAPI();
        return $accountInfo->getCompanyInfo();
    }

    /**
     * Demo table with translations
     * GET /api/demo/grid-data
     *
     * @return array of json Objects
     */
    public function getDemoTableAction()
    {
        return [
          ['id' =>1, 'alignment' => 'Barbarian Queen', 'character' =>'Neutral Evil'],
          ['id'=> 2, 'alignment' => "Global Senior Vice President of Sales", 'character' =>"Chaotic Evil"],
          ['id'=> 3, 'alignment' => "Jonathan the Piggy", 'character' =>"Glorious Good"],
          ['id'=> 4, 'alignment' => "Paladin Knight", 'character' =>"Lawful Good"],
          ['id'=> 5, 'alignment' => "Potato Chip", 'character' =>"Radiant Good"]
        ];
    }

    /**
     * List of documents type Invoice
     * GET /api/documents/documents
     *
     * @return array|json Objects
     */
    public function getDocumentsInvoicesAction(Request $request)
    {
        $docType = 'invoice'; // default value
        if ($request->get('documentType'))
        {
            $docType = $request->get('documentType');
        }

        $documents = new TsRestAPI();
        return $documents->getDocumentList($docType);
    }

    /**
     * Get all list of translation due to the locale
     * GET /api/locale/translations
     * @return array
     */
    public function getTranslationsAction()
    {
        $session = new Session();

        return $this->get('translator')
            ->getCatalogue($session->get('_locale'))->all('messages');
    }

    /**
     * Check is server running
     * GET /api/health
     *
     * @return array |json Objects
     */
    public function checkHealthAction()
    {
        $this->get('translator')
            ->setLocale(
                $this->get('session')->get('_locale')
            );
        return $this->get('translator')->trans('Message.Good job');
    }

}
